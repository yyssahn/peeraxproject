#!/usr/bin/python
import sys, json, xmpp, random, string, MySQLdb

SERVER = 'gcm.googleapis.com'
PORT = 5235
USERNAME = "1029755535216"
PASSWORD = "AIzaSyAIOTRkELfoFGshQqVPZsEsXrPwffEem1M"
unacked_messages_quota = 100
send_queue = []

# Return a random alphanumerical id
def random_id():
  rid = ''
  for x in range(8): rid += random.choice(string.ascii_letters + string.digits)
  return rid

def message_callback(session, message):
  global unacked_messages_quota
  gcm = message.getTags('gcm')
  regid = ""

  if gcm:
    gcm_json = gcm[0].getData()
    msg = json.loads(gcm_json)
    if not msg.has_key('message_type'):
      # Acknowledge the incoming message immediately.
      send({'to': msg['from'],
            'message_type': 'ack',
            'message_id': msg['message_id']})
      # Queue a response back to the server.

      toUser = msg['data']['to_user']
      fromUser = msg['from']

      db = MySQLdb.connect("localhost","root","rootpw","lnydb")
      cursor = db.cursor()
      try:
        cursor.execute("SELECT regid FROM users WHERE phonenumber = '%s'" % toUser)
        regid = cursor.fetchone()
      except:
        print "SQL Error finding sendee"
      try:
        cursor.execute("SELECT lastname FROM users WHERE regid = '%s'" % fromUser)
        fromUser = cursor.fetchone()
      except:
        print "SQL Error finding sender"

      db.close()

      msg['data']['to_user'] = fromUser[0]
      
      if msg.has_key('from'):
        # Send a dummy echo response back to the app that sent the upstream message.
        send_queue.append({'to': regid[0],
                           'message_id': random_id(),
                           'data': msg['data']})

    elif msg['message_type'] == 'ack' or msg['message_type'] == 'nack':
      unacked_messages_quota += 1

def send(json_dict):
  template = ("<message><gcm xmlns='google:mobile:data'>{1}</gcm></message>")
  client.send(xmpp.protocol.Message(
      node=template.format(client.Bind.bound[0], json.dumps(json_dict))))

def flush_queued_messages():
  global unacked_messages_quota
  while len(send_queue) and unacked_messages_quota > 0:
    send(send_queue.pop(0))
    unacked_messages_quota -= 1

client = xmpp.Client('gcm.googleapis.com', debug=['socket'])
client.connect(server=(SERVER,PORT), secure=1, use_srv=False)
auth = client.auth(USERNAME, PASSWORD)
if not auth:
  print 'Authentication failed!'
  sys.exit(1)

client.RegisterHandler('message', message_callback)

while True:
  client.Process(1)
  flush_queued_messages()
