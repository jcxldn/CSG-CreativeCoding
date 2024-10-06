// 2. (Worked) Example: choose a shape based on mouse position

void setup() {
  size(400, 300);
  fill(203, 118, 122);
}

void draw() {
  background(255, 236, 149);

  // **if** the mouse on the left
  if (mouseX < width/2) {
    // then draw a circle
    ellipse(mouseX, mouseY, 80, 80);
  } else {
    // otherwise (else) draw a rectangle
    rectMode(CENTER); // using given coordinates (eg. mouse) as the centre point
    rect(mouseX, mouseY, 80, 80);
  }
}