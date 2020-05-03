from flask import Flask, request
import random
import uuid 
import string
import json
from flask_cors import CORS, cross_origin

app = Flask(__name__)
cors = CORS(app)
app.config['CORS_HEADERS'] = 'Content-Type'

@app.route('/users/<userId>/trips/<tripId>', methods = ['POST'])
@cross_origin()
def post_trip(userId, tripId):
    response = {"userId" : "ciao", "jwtBearer" : "bearer"}
    return json.dumps(response),200,{'content-type':'application/json'}

@app.route('/users/auth', methods = ['POST'])
@cross_origin()
def get_execution_structured_inputs():
    response = {"userId" : "ciao", "jwtBearer" : "bearer"}
    return json.dumps(response),200,{'content-type':'application/json'}

@app.route('/users', methods = ['POST'])
@cross_origin()
def storeUser():
    return json.dumps({}),200,{'content-type':'application/json'}

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=1339)

