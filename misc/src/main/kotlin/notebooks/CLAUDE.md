# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

KotlinEtudes is a multi-module Gradle project exploring Kotlin programming concepts and algorithms. It contains two main modules:
- `misc`: Core algorithms, data structures, and language features (main development area)
- `retrofittestdrive`: HTTP client resilience patterns with Retrofit


## Development Preferences

### Code Organization
- **Utility functions**: Extract reusable logic into utility files (e.g., `TyobeUtils.kt`) rather than inline code
- **Function design**: Prefer simple, single-purpose functions that accept basic types (`String`, `Any?`) over complex DataFrame-specific functions
- **Documentation**: All utility functions must have comprehensive KDoc with `@param`, `@return`, and `@sample` sections

### Notebook Development and patterns

The project contains Kotlin Notebooks. The notebooks use 
- Kotlin Dataframe. GitHub repo:https://github.com/Kotlin/dataframe . API documentation: https://kotlin.github.io/dataframe/guides-and-examples.html
- Kotlin Kandy. GitHub repo: https://github.com/Kotlin/kandy. Documentation https://kotlin.github.io/kandy/examples.html 
for visualisation.

- **Data transformation**: Use DataFrames fluently with method chaining for transformations
- **Avoid mutation**: Don't overwrite original data files; work with transformed DataFrames in memory.
- **Visualization sizing**: Charts should be sized appropriately (e.g., `size = 1000 to 600`) for readability
- **Column operations**: Use `cols { condition }` syntax for bulk column transformations instead of naming columns individually
- **Data reshaping**: Prefer `gather().into()` for wide-to-long transformations over manual iteration
- **Type safety**: Convert raw CSV string data to appropriate types early in the pipeline
- **Error handling**: Use utility functions that gracefully handle null/invalid data (return 0.0 for numeric conversions)
- **API usage**: Use DataFrame's fluent API: `.filter()`, `.sortByDesc()`, `.take()`, `.gather()`, `.remove()`

### Kotlin DSL Build System
- **Build files**: Use Kotlin DSL (`.gradle.kts`) instead of Groovy (`.gradle`) for all build scripts
- **Version catalogs**: Centralize dependency versions in `settings.gradle.kts`, reference via `libs.*` in modules

## Build Commands

```bash
# Build entire project
./gradlew build

# Build specific module
./gradlew misc:build
./gradlew retrofittestdrive:build

# Run all tests
./gradlew test

# Run tests for specific module
./gradlew misc:test
./gradlew retrofittestdrive:test

# Clean build artifacts
./gradlew clean

# Assemble without running tests
./gradlew assemble
```

## Testing Framework

Uses **Kotest** framework for testing with StringSpec style:
- Test files end with `Test.kt`
- Tests use descriptive string names: `"test description" { ... }`
- Assertions use Kotest matchers: `result shouldBe expected`
- Property-based testing available via `kotest-property`

## Project Structure

### misc/ module (Primary codebase)
- `datastructs/`: Advanced data structures (BinaryArraySet, BST, heaps)
- `rosettacode/`: Algorithm implementations from Rosetta Code challenges
- `language/`: Kotlin language feature explorations (value classes, contracts, interfaces)
- `misc/`: Utility algorithms (sorting, string processing, math)
- `crypto/`: Cryptographic implementations
- `folding/`: Functional programming patterns

### retrofittestdrive/ module
- HTTP client patterns with Retrofit2, Gson, and Resilience4j
- Mock server testing with MockServer

## Key Technical Details

- **Java/Kotlin versions**: Java 23, Kotlin 2.2.0
- **Dependency management**: Gradle version catalogs in `settings.gradle`
- **Testing**: JUnit Platform with Kotest 5.9.1
- **Code style**: Official Kotlin style (configured in `gradle.properties`)
- **Parallel builds**: Enabled in `gradle.properties`

## Dependencies

Common libraries across modules:
- Apache Commons Math3 for mathematical functions
- Kotest bundle (runner, assertions, property testing, datatest)
- Guava and Coroutines (misc module)
- Retrofit2, Gson, Resilience4j, SLF4J (retrofittestdrive module)

## Running Individual Tests

Single test class:
```bash
./gradlew misc:test --tests "datastructs.BinaryArraySetTest"
./gradlew retrofittestdrive:test --tests "retrofittestdrive.RetrofitTest"
```

## Development Notes

- Most algorithm implementations include diagnostic methods for internal state validation
- Resource files (sudoku puzzles, test data) stored in `src/main/resources/`
- Property-based testing examples in `proptesing/PropTesting.kt`
- Jupyter notebook integration for data analysis (`Kotebook.ipynb`)