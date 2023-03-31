from flask import Flask, request
from flask_mysqldb import MySQL
from functools import wraps
from werkzeug.security import generate_password_hash, check_password_hash
import jwt
import datetime
import sys
import os
from dotenv import load_dotenv

load_dotenv();

app = Flask(__name__)
app.config['MYSQL_HOST'] = '127.0.0.1'
app.config['MYSQL_PORT'] = os.getenv('SQLPORT')
app.config['MYSQL_USER'] = 'root'
app.config['MYSQL_PASSWORD'] = os.getenv('SQLPASS')
app.config['MYSQL_DB'] = 'cs7319'
app.config['SECRET_KEY'] = os.getenv('SECRETKEY')

mysql = MySQL(app)
# mysql.init_app(app)

# print(os.getenv('SQLPASS'), file=sys.stderr)

def token_required(f):
    @wraps(f)
    def decorated(*args, **kwargs):
        token = request.args.get('token')
        if not token:
            return 'Missing AUTH'
        try:
            data = jwt.decode(token, os.getenv('SECRETKEY'))
        except:
            return 'Invalid AUTH'
        return f(*args, **kwargs)
    return decorated

@app.before_first_request
def before_first_request():
    try:
        print(mysql.connection.ping(), file=sys.stderr)
        print("connection successful", file=sys.stderr)
    except Exception as e:
        print("error connecting to server", e, file=sys.stderr)

@app.route('/newuser', methods=['POST'])
def new_user():
    name = request.form['fullName']
    workid = request.form['workID']
    phone = request.form['phoneNumber']
    email = request.form['email']
    password = request.form['password']
    cur = mysql.connection.cursor()
    check = cur.execute("SELECT * FROM users WHERE email=%s", [email])
    if check > 0:
        return 'Username already exists!'
    else:
        password_hash = generate_password_hash(password)
        cur.execute("INSERT INTO users(fullName, workID, phoneNumber, email, password) VALUES (%s, %s, %s, %s, %s)", (name, workid, phone, email, password_hash))
        mysql.connection.commit()
        cur.close()
        return 'User added'
    
@app.route('/login', methods=['POST'])
def login():
    email = request.form['email']
    password = request.form['password']
    cur = mysql.connection.cursor()
    result = cur.execute("SELECT * FROM users WHERE email=%s", [email])
    if result > 0:
        data = cur.fetchone()
        password_hash = generate_password_hash(password);
        if check_password_hash(password_hash, password):
            token = jwt.encode({'email': email, 'exp': datetime.datetime.utcnow() + datetime.timedelta(minutes=30)}, app.config['SECRET_KEY'])
            return token
        else:
            return 'Invalid password!'
    else:
        return 'Invalid email!'
    
@app.route('/getshifts')
@token_required
def get_shifts():
    username = request.args.get('username')

if __name__ == '__main__':
    app.run(port=8080)