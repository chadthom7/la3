# Makefile for JAVA network programs

default: Client.class chat_server.class chat_client.class

# Server
chat_server.class: chat_server.java
	javac chat_server.java
Client.class: Client.java
	javac Client.java

# Client
chat_client.class: chat_client.java
	javac chat_client.java

clean:
	rm -rf *.class \
