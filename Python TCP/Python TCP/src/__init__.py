import socket

TCP_IP = '127.0.0.1'
TCP_PORT = 6787
BUFFER_SIZE = 30

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind((TCP_IP, TCP_PORT))
s.listen(1)
print "Waiting for client to connect."

connection, address = s.accept()
print 'Connected to: ', address

while 1:
    data = connection.recv(BUFFER_SIZE)
    end = 'END'
    if data == end: break
    connection.send(data) #echo
conn.close()