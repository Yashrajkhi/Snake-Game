# 🐍 Snake Game — Java Swing

A clean, fully featured Snake Game built in Java using Swing. No external libraries required — just the JDK.

![Java](https://img.shields.io/badge/Java-17%2B-orange?style=flat-square&logo=java)
![License](https://img.shields.io/badge/License-MIT-green?style=flat-square)

---

## 📸 Preview

```
┌─────────────────────────────┐
│  Score: 80        Best: 120 │
│                             │
│        🍎                   │
│   ████████>                 │
│                             │
└─────────────────────────────┘
```

---

## ✨ Features

- Smooth grid-based movement with a 25×25 board
- Increasing speed as your score grows
- Self-collision and wall-collision detection
- High score tracking (per session)
- Animated snake head with directional eyes
- Glowing food effect
- Game Over screen with restart support
- Supports both Arrow Keys and WASD

---

## 🚀 Getting Started

### Prerequisites

- Java **17 or higher** installed
- A terminal (Linux/Mac) or Command Prompt (Windows)

### Clone the Repository

```bash
git clone https://github.com/YOUR_USERNAME/SnakeGame.git
cd SnakeGame
```

### Build & Run

**Linux / macOS:**
```bash
chmod +x build.sh
./build.sh
```

**Windows:**
```bat
build.bat
```

**Manual (any OS):**
```bash
mkdir out
javac -d out $(find src -name "*.java")   # Linux/Mac
java -cp out com.snakegame.Main
```

---

## 🕹️ Controls

| Key | Action |
|-----|--------|
| `↑` or `W` | Move Up |
| `↓` or `S` | Move Down |
| `←` or `A` | Move Left |
| `→` or `D` | Move Right |
| `Enter` | Start / Restart |

---

## 📁 Project Structure

```
SnakeGame/
├── src/
│   └── com/snakegame/
│       ├── Main.java        # Entry point
│       ├── GameFrame.java   # JFrame window
│       ├── GamePanel.java   # Game loop, rendering, input
│       ├── Snake.java       # Snake logic & movement
│       ├── Food.java        # Food spawning
│       └── Direction.java   # Direction enum
├── build.sh                 # Linux/macOS build script
├── build.bat                # Windows build script
├── .gitignore
└── README.md
```

---

## 🎮 Gameplay

- Eat the red food to grow and earn **+10 points**
- The snake speeds up every time you eat
- Hitting a wall or your own body ends the game
- Press **Enter** to restart after a game over

---

## 📜 License

This project is licensed under the [MIT License](LICENSE).

---

## 🤝 Contributing

Pull requests are welcome! Feel free to:
- Add sound effects
- Implement a pause feature
- Add difficulty levels
- Save high scores to a file
