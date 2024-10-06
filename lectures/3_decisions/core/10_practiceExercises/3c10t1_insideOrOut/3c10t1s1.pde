// 10. Practice Exercises
//     -> Coding Task 1: "Inside or Out?"
//        -> After Step 1

float x, y;

void setup() {
  size(300,200);
  
  // random(0,5) -> 0-4.9999 values
  x = random(width); // random(high); or random(low, high);
  y = random(height);
}

void draw() {
  background(#9D9D9D);
  circle(x, y, 50);
}