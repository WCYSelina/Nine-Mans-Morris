Disclaimer: Please note that this project is a collaborative effort conducted by a group of students from Monash University. All contributors are currently enrolled in the university and have worked jointly on this project.

# Nine Mens Morris
## Project Details

**Objective:**

- The objective of this project is to create a fully-functional, terminal-based version of the classic board game Nine Men's Morris. Our goal is to replicate the traditional gameplay experience within a command-line interface, allowing for intuitive user interactions and strategic play without the need for a graphical user interface. This project aims to provide an accessible, text-based game that challenges players to think critically and plan ahead, while also offering a nostalgic return to the simplicity of classic games.

## Usage

- Setup: download ```project.jar``` in the directory
- Open up your terminal
- Run ```java -jar <project_location>```

## Code Structure
<img width="361" alt="image" src="https://github.com/WCYSelina/Nine-Mans-Morris/assets/95896839/001eecf4-19ab-4d16-9fe7-a82c3c984676">

As depicted in the project structure image above, our codebase is organized following the Model-View-Controller (MVC) architectural pattern. This design principle separates concerns as follows:

- **Model**: Contains the core functionality and data structures of our Nine Men's Morris game, including classes like `Board`, `Mill`, `Player`, and `Token`.

- **View**: Handles all the user interface components, which in our terminal-based application includes classes such as `BoardView`, `GameView`, `MenuView`, and `ErrorView`.

- **Controller**: Manages the communication between the Model and View, and includes classes such as `Game`, `MenuController`, and `BoardController`.

This separation of concerns ensures that our application is modular, making it easier to maintain and extend in the future. Each component can be developed and modified independently, reducing the complexity of the codebase and improving the scalability of our application.

## GamePlay

**Menu**
![image](https://github.com/WCYSelina/Nine-Mans-Morris/assets/95896839/fb96fada-f256-4958-aaea-c41bdce28287)

The above image is the menu of the program where 2 player can play together or a single player can play with an AI

**Play**

![CL_Monday6pm_Team7_DemoVideo](https://github.com/WCYSelina/Nine-Mans-Morris/assets/95896839/32fd2bc6-fcfc-436b-afa2-99bffbc0c33f)







