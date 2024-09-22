# Chat-Socket Application

A real-time chat application using React for the frontend and Spring Boot with WebSocket for the backend. This app allows users to add friends and send messages in real-time.

**Features**:
- Real-time chat with WebSockets.
- Multiple chat rooms.
- Users can add friends and chat with them.
- Message history is saved in the database.
- REST APIs for user, chat room, and message management.
- Emoji and image support.

**Technologies**:

Frontend:
- React: UI framework
- Redux Toolkit: State management
- STOMP.js and SockJS: WebSocket communication
- Axios: For making REST API calls
- Firebase: Used for storing images
- Emoji Picker React: Emoji support for chats
- React Toastify: For notifications
- Sass: For styling

Backend:
- Java 17
- Spring boot 3.3


## Getting started with Frontend
Ensure you have Node.js version >= 20.17 installed.

> *_Warning:_* If you have Node.js version <= 20.17: You may need to uninstall your current Node.js version and install Node.js >= 20.17. Alternatively, you can use NVM (Node Version Manager) to switch between versions.
#### Using NVM (Node Version Manager) to switch between Node.js versions (optional):
- Uninstall your current Node.js version (note the version if you need to reinstall it later).
- Download and install nvm-setup.exe (any version) from [this GitHub repository](https://github.com/coreybutler/nvm-windows/releases).
- Open the command prompt and run the following commands (you can choose any version >= 20.17.0 that you prefer):
```
nvm install 20.17.0
nvm use 20.17.0
```
- Verify your current Node.js version by running: 
```
node -v
```
### Running the Frontend
1. Open the frontend project in Visual Studio Code.
2. Open the terminal by pressing ```(Ctrl + Shift + `)```.
3. Run the following command to install libraries:
```
npm i
```
4. Start the development server by running:
```
npm run dev
```
## Getting started with Backend
The backend is already fully configured, including Liquibase for database migrations and other dependencies. To get started, simply follow these steps:

1. Install JDK 17: Ensure that JDK 17 is installed on your machine. If not, download and install it from [the official JDK website](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html). 
- To verify your JDK version, run:
```
java -version
```
2. **Open the Project**: Open the project in your preferred IDE (such as IntelliJ).
3. **Build the Project**: Clean and install the Maven project by running:
```
mvn clean install
```
4. **Run the Project**: Start the application with:
```
mvn spring-boot:run
```
There’s no additional setup needed. Everything, including database migrations and dependencies, is already handled!
