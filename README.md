# SimpleLang Compiler (CPY Language)

This repository contains my 3-phase implementation of a **compiler for a C-like language (SimpleLang)**, designed during the *Compiler Design* course at the University of Tehran (Spring 1404).  
The project gradually builds a working compiler through syntax tree construction, name analysis and optimization, and type/vulnerability checking.

---

## 🧩 Overview

The compiler processes a simplified C-like language called **CPY**, using **ANTLR4** for parsing and a **visitor-based design** for all analysis phases.

| Phase | Title | Description |
|--------|--------|-------------|
| Phase 1 | AST Construction & Syntax Analysis | Builds the Abstract Syntax Tree (AST) and performs structural checks (expression depth, statement counting). |
| Phase 2 | Name Analysis & Optimization | Performs symbol table generation, undeclared variable detection, and static code optimizations. |
| Phase 3 | Type Checking & Vulnerability Analysis | Adds semantic checking for type mismatches and analyzes basic memory vulnerabilities. |

---

## ⚙️ Phase 1 – AST Construction & Syntax Analysis
 
**Main Goals:**
- Build an **Abstract Syntax Tree (AST)** for SimpleLang using ANTLR4.  
- Define node hierarchy (`Node`, `Expression`, `Statement`, etc.).  
- Traverse AST to:
  - Count statements per scope  
  - Compute expression depth  
  - Convert code from **C-style** syntax to **CPY (Python-like)** indentation syntax.  

**Example:**  
```c
for(i=0;i<3;i++){
  printf(i);
}
```

becomes:

```c
for i=0;i<3;i++:
    printf(i)
end
```

## ⚙️ Phase 2 – Name Analysis & Code Optimization

Main Goals:

    Implement Name Analysis using a Symbol Table:

        Detect undeclared variables/functions.

    Apply Static Optimizations:

        Remove unused variables/parameters

        Eliminate dead code after return

        Remove side-effect-free expressions

        Merge redundant assignments

        Replace typedefs and constants (CS students)

        Perform reachability analysis from main (keep only reachable code)

Example Error:

Line:12 -> x not declared

Example Optimization:

```c
int x = 2;
x = 3;
printf(x);
```

becomes:

```c
int x = 3;
printf(x);
```

## ⚙️ Phase 3 – Type Checking & Vulnerability Analysis

Main Goals:

    Type Checking

        Argument–parameter type mismatch (ArgumentTypeMismatch)

        Operand type inconsistency (NonSameOperands)

        Return type mismatch (ReturnTypeMismatch)

    Vulnerability Analysis (for CS students):

        Memory leak detection (malloc without free)

        Uninitialized variable usage

        User-controlled malloc detection

Example Errors:

Line:15 -> ArgumentTypeMismatch: expected int, got string
Line:24 -> malloc allocated memory never freed

🧠 Technical Details

    Language: Python 3

    Parser Generator: ANTLR4

    Design Pattern: Visitor Pattern

    AST Traversal: Recursive visitor methods

    Error Handling: Line-based logging

    Symbol Table: Scoped dictionary stack
