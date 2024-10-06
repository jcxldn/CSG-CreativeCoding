// 6. Exploring randomness: coins and dice

float randomNumber;
 
void setup()
{
  size(200, 200);
  fill(0);
  textSize(48);
  textAlign(CENTER);
 
  // Initialise randomNumber between 0 and 2 (inclusive)
  randomNumber = random(2);
}
 
void draw()
{
  background(230, 255, 230);
 
  String message;
  
  // me/lecturer ans
  // lecturer: this **could** also be in setup() IF the declaration "String message;" is global (ie. not written in a function)
  // but globals should ideally be avoided - always use local if you can.
  if (randomNumber < 1) {
    // less than 1, set message to TAILS
    message = "TAILS";
  } else {
    // otherwise set message to HEADS
    message = "HEADS";
  }
  
  // Extra: since we are just changing message we can put text() call here,
  // if statements don't have their own variable scope iirc
  // -> functions do have scope
  // So the updated message can be accessed here.
  text(message, width/2, height/2);
}