/* DashArd simple
 *
 * by Javier Alsina
 */

#include <Ethernet.h>
#include <SPI.h>
#include "DashArd.h"


DashArd client = DashArd("accessId");

//Setup
void setup() {
  Serial.begin(9600);
  client.init();
}

String response;
void loop(){
  response = "";
  
  client.setMeasure(21,"3.4");
  client.pinsPoll();
  
  client.syncronize();
  
  delay(1000);
}
