# Networked Software Development - Assignment

# Classes
- Client
  -
  - Contains client constructor and functions to send requests to the server.
- Server
  -
  - Contains a clock to generate timestamps and a multithreaded client handler that processes requests sent by the client concurrently.
- Message
  -
  - Constructs messages, serializes and deserializes them to and from JSON.
- MessageBoardGUI
  -
  - Used to initialise the user interface. GUI elements are loaded from source.fxml.
- MessageBoardController
  -
  - Contains GUI element behaviour code.
- Database
  -
  - Processes SQL queries.

# Interfaces and their Implementations
- Request
  -
  - PostRequest
  - ReadRequest
  - QuitRequest
- Response
  -
  - SuccessResponse
  - ErrorResponse
  
# Included Files
- json-simple-1.1.1.jar
- identifier.sqlite
- sqlite-jdbc-3.27.2.1.jar
