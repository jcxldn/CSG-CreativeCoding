// 6.1  Exploring randomness: coins and dice

// before else,if (shown in 7)
// not space efficient


float ran;
 
void setup()
{
  size(200, 200);
  fill(0);
  textSize(48);
  textAlign(CENTER);
 
  ran = random(6);
}
 
void draw()
{
  background(255);
  String message="";
 
  if (ran >= 0 && ran < 1)
  {
    message = "ONE";
  }
 
  if (ran >= 1 && ran < 2)
  {
    message = "TWO";
  }
 
  if (ran >= 2 && ran < 3)
  {
    message = "THREE";
  }
  
 // 
  if (ran >= 3 && ran < 4)
  {
    message = "FOUR";
  }
  
 
  if (ran >= 4 && ran < 5)
  {
    message = "FIVE";
  }
  
 
  if (ran >= 5 && ran < 6)
  {
    message = "SIX";
  }
 
  text(message, width/2, height/2);
}