# Grace Language Compiler

A CUP/JFlex-based compiler for the Grace programming language.

## Features

- ? **Lexical Analysis**: Complete tokenization of Grace language constructs
- ? **Syntax Analysis**: Recursive descent parser supporting full Grace grammar
- ? **Semantic Analysis**: Type checking and semantic validation
- ? **Program Analysis**: Structure analysis and statistics
- ? **Code Generation**: (Planned)
- ? **Interpreter**: (Planned)

## Supported Grace Language Features

- Function definitions with parameters and return types
- Local function definitions and declarations
- Variable declarations (`var x : int;`)
- Basic data types (`int`, `char`, `nothing`)
- Array types (`int[]`, `char[]`)
- Built-in functions (`puts()`, `geti()`)
- String literals
- Arithmetic expressions (`+`, `-`, `*`, `div`, `mod`)
- Conditional statements (`if-then-else`)
- Control flow statements
- Function calls with parameters

## Grammar Structure

The compiler follows the formal Grace grammar:
```
program ::= func_def
func_def ::= header local_def_list block
header ::= fun id ( fpar_def_list ) : ret_type
local_def ::= func_def | func_decl | var_def
```

## Installation

### Prerequisites
- Java 8 or higher
- Maven 3.6 or higher

### Build
```bash
mvn clean compile
```

## Usage

### Using the Launcher Script (Windows)
```bash
# Compile a Grace program
grace.bat program.grace

# Compile with verbose output
grace.bat -v program.grace

# Analyze program structure
grace.bat -a program.grace

# Show help
grace.bat --help
```

### Using Java Directly
```bash
java -cp "target/classes;path/to/cup-runtime.jar" gr.hua.dit.compiler.Main [options] <grace_file>
```

### Command Line Options

- `-v, --verbose`: Enable verbose output showing compilation phases
- `-a, --analyze`: Analyze program structure and show statistics
- `-i, --interpret`: Interpret and execute the program (planned)
- `-h, --help`: Show help message
- `-Ddebug=true`: Enable debug output (shows AST structure)

## Example Grace Programs

### Simple Function
```grace
fun factorial (n : int) : int
var result : int;
{
  if n <= 1 then
    result <- 1;
  else
    result <- n * factorial(n - 1);
  
  return result;
}
```

### Towers of Hanoi
```grace
fun hanoi () : nothing
var n : int;
fun move (from : int; to : int) : nothing
{
  puts("Move disk from tower ");
  puts(from);
  puts(" to tower ");
  puts(to);
}
fun solve (n : int; from : int; to : int; aux : int) : nothing
{
  if n = 1 then
    move(from, to);
  else {
    solve(n - 1, from, aux, to);
    move(from, to);
    solve(n - 1, aux, to, from);
  }
}
{
  n <- 3;
  solve(n, 1, 3, 2);
}
```

## Architecture

- **Lexer** (`lexer.jflex`): JFlex-generated lexical analyzer
- **Parser** (`parser.cup`): CUP-generated LR parser
- **AST Classes** (`ast/`): Abstract Syntax Tree node implementations
- **Type System** (`types/`): Type checking and inference
- **Symbol Table** (`Symbol/`): Scope management and symbol resolution
- **Error Handling** (`errors/`): Semantic and type error reporting
- **Main Compiler** (`Main.java`): Command-line interface and compilation phases

## Testing

The compiler has been tested with:
- ? Simple function definitions
- ? Complex recursive functions
- ? Local function definitions
- ? Variable declarations
- ? String literals and built-in functions
- ? Conditional statements and expressions
- ? Error handling and recovery

## Development

### Adding New Features
1. Update the lexer (`src/main/jflex/lexer.jflex`) for new tokens
2. Update the parser (`src/main/cup/parser.cup`) for new grammar rules
3. Add/modify AST classes in `src/main/java/gr/hua/dit/compiler/ast/`
4. Update semantic analysis as needed
5. Rebuild with `mvn clean compile`

### Debug Mode
Enable debug output to see the AST structure:
```bash
java -Ddebug=true gr.hua.dit.compiler.Main program.grace
```

## License

This project is for educational purposes as part of compiler construction coursework.
