// 4. Nested Ifs

void setup()
{
  size(400, 300);
}
 
void draw()
{
  background(18, 48, 193);
 
  if (mouseY < height/2)
  {
    if (mouseX < width/2)
    {
      fill(250, 207, 35);  // colour for sun
    } 
    else
    {
      fill(255, 255, 255);  // colour for moon
    }
    ellipse(mouseX, mouseY, 80, 80);
  } 
  else
  {
    // else statement: mouse is on the bottom
    if (mouseX < width/2) {
      // Mouse on left side
      fill(#FF891A);
    } else {
      // Mouse on right side
      fill(#1AFF2A);
    }
    //fill(50, 50, 15);  // colour for ground
    rect(mouseX, mouseY, 80, 80);
  }
}