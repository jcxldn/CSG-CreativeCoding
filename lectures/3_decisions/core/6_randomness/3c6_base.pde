// 6. Exploring randomness: coins and dice

float randomNumber;
 
void setup()
{
  size(200, 200);
  fill(0);
  textSize(48);
  textAlign(CENTER);
 
  // Initialise randomNumber between 0 and 2
  randomNumber = random(2);
}
 
void draw()
{
  background(230, 255, 230);
 
  String message;
 
  message = "TAILS";
 
  text(message, width/2, height/2);
}