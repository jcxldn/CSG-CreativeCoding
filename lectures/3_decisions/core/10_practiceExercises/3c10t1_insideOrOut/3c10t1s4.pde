// 10. Practice Exercises
//     -> Coding Task 1: "Inside or Out?"
//        -> After Step 4

float x, y;

int rWidth = 130;
int rHeight = 20;

void setup() {
  size(300, 200);

  // random(0,5) -> 0-4.9999 values
  x = random(width); // random(high); or random(low, high);
  y = random(height);
}

void draw() {
  background(#9D9D9D);
  rectMode(CENTER);
  
  // Set circle to white (will be changed to red if necessary, see below check)
  fill(#FFFFFF);
  
  // Check if NOT inside rect
  if (!inRectBound()) {
    // Check if inside circle bounds
    if (dist(x, y, mouseX, mouseY) <= 50/2) { // -25 -> 25
      // Step 4 condition: not inside rect but inside circle
      // therefore fill circle red
      fill(#FF0000);
    }
  }
  
  circle(x, y, 50);

  // Check bounds for rect and set fill accordingly
  if (inRectBound()) {
    // Inside rect
    fill(#0000FF);
  } else {
    // Outside rect
    fill(#FFFFFF);
  }
  // Draw rect
  rect(x, y, rWidth, rHeight);
}

boolean inBound(int pos, float coord, int size) {
  //println(rWidth, mouseX, mouseX-x);
  // mouseX-x range -(rWidth/2) -> +(rwidth/2)
  //return ((val-x) > (size/2) && (val-x) < (size/2));
  return (pos-coord) > -(size/2) && (pos-coord) < (size/2);
}

boolean inRectBound() {
  return inBound(mouseX, x, rWidth) && inBound(mouseY, y, rHeight);
}