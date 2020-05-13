# Part 1

## variable
- values (val)
    - val x : type = vals
    - vals are immutable
    - vals can be inferred by compiler (don't have to specify)
  
- variable (var)
    - var aVariable : type = variable
    - mutable (correct type)
    - side effects
        - changing var =, += ....
        - print
        - while
        - etc
- prefer vals over vars
    
## Expression
- Instruction (Do) vs Expression (Value)
    - Scala  ====== EXPRESSION
    - Scala discourage loop!!
    - Everything in Scala is an expression
- expression 
    - like val x = 1+2
        - 1+2 is math expression
    - if returns a value 
        - if(aCondition) 5 else 3
    - Unit == void
        - side effect returns unit
- Code blocks 
    - = expression
    - val block = val last expression
    - anything inside code block stays inside

## Function
- WHEN YOU NEED LOOPS USE RECURSION !!! 
- name(paramName: type, ...): returnType = {}

## Type inference
- compiler can figure val type
- compiler can figure function return type
    - recursion need to be stated

## Recursion
- Use tail-recursion to preserve stack memory!!!!

## Call by Name & Value
- Call by value
    - calculate first before passing to function
    - same value used everywhere
- Call by name =>
    - expression is passed literally
    - evaluate different time (at every use within)
    
##Default and Named arguments
- name all the parameter and pass them in any order


##String operation
- S-interpolator 
    - s"...$val....${expression}"
- F-interpolator
    - f".....$val%2.2f" -- like in C
- raw-interpolator
    - any special str like \n will be escaped in raw"....\n.."
        - but inject will not be escaped


    
