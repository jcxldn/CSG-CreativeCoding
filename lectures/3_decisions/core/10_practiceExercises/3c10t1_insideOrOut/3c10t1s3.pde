// 10. Practice Exercises
//     -> Coding Task 1: "Inside or Out?"
//        -> After Step 3

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
  fill(#FFFFFF);
  circle(x, y, 50);
  rectMode(CENTER);

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