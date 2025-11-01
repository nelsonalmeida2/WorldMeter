# 🌍 World Meter: Global Statistics Engine

Welcome to **World Meter** — a **Java** backend solution for processing and querying global statistical data. This project is designed as a **Command-Line Interface (CLI)** application to efficiently parse large datasets, clean them, and expose complex metrics in real-time.

The project focuses on efficient data manipulation, highlighting skills in **data structures** and **algorithm optimization**.

---

## 🛠️ Technologies Used

- **Language:** Java

---

## 🚀 Setup and Installation Guide

Follow these steps to set up and run the project locally.

### 1️⃣ Clone the Repository

```bash
git clone https://github.com/nelsonalmeida2/deisi-world-meter.git
cd deisi-world-meter
```

### 2️⃣ Setup (IDE - Recommended)

The easiest way to run the project is through an IDE (like IntelliJ IDEA, Eclipse, or VS Code).

1.  Open the project in your IDE (open the `deisi-world-meter` folder).
2.  The IDE should automatically detect the project structure.
3.  Find the file `src/pt/ulusofona/aed/deisiworldmeter/Main.java`.
4.  Right-click on the file and select **"Run 'Main.main()'"**.

### 3️⃣ Compilation and Execution (Command-Line)

If you prefer to run via the terminal:

#### Compile the Project

```bash
# (From the project root)
# Create an 'out' directory for the .class files
mkdir out

# Compile all .java files
javac -d out $(find src -name "*.java")
```

#### Run the Application

```bash
# Run the Main class, including 'out' in the classpath
java -cp out pt.ulusofona.aed.deisiworldmeter.Main
```
---

## 🚀 Available Commands

The application offers a powerful set of commands for analysis, querying, and data manipulation.

### 📊 Statistical Analysis
* `COUNT_CITIES <MinPopulation>`
* `SUM_POPULATIONS <CountryName>`
* `GET_MOST_POPULOUS <N>`
* `GET_LEAST_POPULOUS <N>`
* `GET_DUPLICATE_CITIES <MinPopulation>`
* `GET_DUPLICATE_CITIES_DIFFERENT_COUNTRIES <MinPopulation>`
* `GET_COUNTRIES_GENDER_GAP <MinGap>`
* `GET_TOP_POPULATION_INCREASE <YearStart> <YearEnd>`

### 🌍 Geographical Queries
* `GET_CITIES_BY_COUNTRY <N> <CountryName>`
* `GET_TOP_CITIES_BY_COUNTRY <N> <CountryName>`
* `GET_CITIES_AT_DISTANCE <Dist> <CityName>`
* `GET_CITIES_AT_DISTANCE2 <Dist> <CityName>`

### 📈 Historical Data
* `GET_HISTORY <YearStart> <YearEnd> <CountryName>`
* `GET_MISSING_HISTORY <YearStart> <YearEnd>`

### ✏️ Data Manipulation
* `INSERT_CITY <Alfa2> <Name> <Region> <Population>`
* `REMOVE_COUNTRY <CountryName>`

### ⚙️ Utilities
* `HELP`: Displays the list of all available commands.
* `QUIT`: Exits the application.

---

* 👨‍💻 **Developed by:** Nelson Almeida
* 📅 **Version:** 1.0.0
* 📦 **License:** MIT


