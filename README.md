# Networked Software Development - Assignment
# Compilation Instructions
To compile this program, enter the following command into the command prompt/terminal:
```
javac -cp json-simple-1.1.1.jar:. *.java
```
To run this program, enter the following command into the command prompt/terminal:
```
java -cp json-simple-1.1.1.jar:. Server.java // THIS MUST RUN FIRST
java -cp json-simple-1.1.1.jar:. MessageBoardGUI.java
```
# Server
When the server runs, it will wait for requests sent by a client. When a request is received, it will be parsed into a JSON object, the correct command will be carried out and a server response will be sent back. The server response can be either a success or an error. Once a response has been made, the server goes back to waiting for another request. All commands will be printed to the console in JSON format, which is useful for debugging and learning JSON encoding. For example:
```
Ross: {"_class":"SuccessResponse"}
```
# Running the Client
When the client runs, a GUI will be displayed from which requests can be sent to the console in a user-friendly manner. The user should first log in with a username before using any functionality. If a new username is entered, it will automatically be entered to the database.

# Classes
- Client
  -
  - Contains client constructor and functions to send requests to the server.
- Server
  -
  - Contains a clock to generate timestamps and a multithreaded client handler that processes requests sent by the client concurrently.
- Message
  -
  - Constructs messages, serializes and deserializes them to and from JSON. Messages contain an author, a timestamp and a message body.
- MessageBoardGUI
  -
  - Used to initialise the user interface. GUI elements are loaded from source.fxml.
- MessageBoardController
  -
  - Contains GUI element behaviour code. For example, this class is responsible for the user being able to press enter in the message box to send a mesage.
- Database
  -
  - Processes SQL queries.

# Interfaces and their Implementations
The post, read and list requests have corresponding image requests so that the server can differentiate between messages and images.
- Request
  -
  - PostRequest
  - PostImageRequest
  - ReadRequest
  - ReadImageRequest
  - QuitRequest
- Response
  -
  - SuccessResponse
  - ErrorResponse
  - MessageListResponse
  - ImageListResponse
  
# Included Files
- json-simple-1.1.1.jar
  - To make use of the JSON library so that JSON encoding is possible.
- identifier.sqlite
  - Database file.
- sqlite-jdbc-3.27.2.1.jar
  - Driver to connect our program to the database.
