#include <SPI.h>
#include <Ethernet.h>
#include "RestClient.h"
#include "ArduinoJson/ArduinoJson.h"
#include "HashMap.h"


class DashArd {

  public:
  
    DashArd();
    DashArd(const char* accessId);

    //Client Setup
    void init();
	
	// send metrics values
	void syncronize();
	
	// read pins from server
	void pinsPoll();
	
	// set metrics values to send
	void setMeasure(const char* code, const char* value);

  private:

	RestClient restClient = NULL;
	
	const char* accessId;
    const char* host = "localhost";
    int port = 8080;
    const char* headers[10];
	const char* contentType = "application/json";
	
 	hashmap_ptr metricsMap = hashmap_new(25, NULL, NULL);
	hashmap_ptr pinsMap = hashmap_new(25, NULL, NULL);
	
	
};
