Homework assignment no. 1, URL
====================================

**Publication date:** 8th October 

**Submission deadline:** 26th October 23:59

General information
-------------------
Dealing with URLs can be tedious sometimes. Especially when it comes to distinguishing between them. Consider for example the following URLs.

- http://domain.com/articles/technology/25-moon-landing?order=asc&read=true
- http://domain.com/articles/technology/25-moon-landing/?order=asc&read=true
- http://domain.com/articles/technology/25-moon-landing?read=true&order=asc
- http://domain.com/articles/technology/25-moon-landing?order=asc&read=true
- http://domain.com:80/articles/technology/25-moon-landing?order=asc&read=true
- http://domain.com/articles/technology/./25-moon-landing?order=asc&read=true
- http://domain.com/articles/technology/public/../25-moon-landing?order=asc&read=true

All of these URLs are pointing to the same location and the list could be further extended. 
The goal of this assignment is to write a URL implementation which can correctly distinguish between equal and truly different URLs.

### Few Rules
For the purpose of this implementation, students are forbidden to use ```java.net.URL```, ```java.net.URI```, as well as any other classes or libraries which already implement the functionality defined by this assignment. 
If you are not sure whether it's ok to use some class, **it's always better to ask**.   

### Canonical form of URL

- Port is omitted if it is the default port for given protocol 
- No trailing slashes
- Relative path segments referring to current or parent directory are resolved
- Order of query parameters is fixed

**URLs with are equal if and only if their Canonical form is Equal**

### Errors in URL Structure 
- Protocol is missing (e.g ```mydomain.com/foo```)
    - Such URL is considered **invalid**
- Second or first level domain is missing (e.g. ``http://domain/foo``)
    - Such URL is considered **invalid**
- Relative path segment resolves outside of root (e.g. ```http://domain.com/../foo```)
    - Such URL is considered to have **empty path** (e.g. ``http://domain.com``)
- Invalid characters in domain/path/query
    - Outside the scope of this assignment
    
### Evaluation
The maximum number of points for this assignment is **7**.

- **5 points** for passing tests (attached tests do not guarantee a 100% correctness).
- **2 points** for correct and clean implementation (evaluated by your class teacher).

In cases **when provided tests do not pass** with submitted solution you can be granted no more than **4 points** (this means that you can be granted **0 points** as well)!

### Preconditions
To successfully implement this assignment you need to know the following

1. What is the difference between class and object and how to work with both.
2. What are interfaces and how to use them.
3. What is inheritance and how it helps to avoid duplicities in your code.
4. How to use basic control structures such as if, while and switch.
5. How to work with Strings in java

### Project structure
The structure of project provided as a base for your implementation should meet the following criteria.

1. Package ```cz.muni.fi.pb162.hw01``` contains classes and interfaces provided as part of the assignment.
  - **Do not modify or add any classes or subpackages into this package.**
2. Package  ```cz.muni.fi.pb162.hw01.impl``` should contain your implementation.
  - **Anything outside this package will be ignored during evaluation.**

### Names in this document
Unless fully classified name is provided, all class names are relative to  package ```cz.muni.fi.pb162.hw01``` or ```cz.muni.fi.pb162.hw01.impl``` for classes implemented as part of your solution.

### Compiling the project
The project can be compiled and packaged in the same way you already know 

```bash
$ mvn clean compile
```

The only difference is, that unlike with seminar project, this time checks for missing documentation and style violation will produce an error. 
You can temporarily disable this behavior when running this command. 

```bash
$ mvn clean compile -Dcheckstyle.fail=false
```

You can consult your seminar teacher to help you set the ```checkstyle.fail``` property in your IDE (or just google it). 


### Submitting the assignment
The procedure to submit your solution may differ based on your seminar group. However generally it should be ok to submit ```target/homework01-2019-1.0-SNAPSHOT-sources.jar``` to the homework vault.

Step 1: 
---------------------------
Create ```Url``` class by implementing the ```SmartUrl``` interface. 
All the necessary implementation info is in JavaDocs. 
