#include "DashArd.h"

#ifdef HTTP_DEBUG
#define HTTP_DEBUG_PRINT(string) (Serial.print(string))
#endif

#ifndef HTTP_DEBUG
#define HTTP_DEBUG_PRINT(string)
#endif

DashArd::DashArd(){
  accessId = "NOT_DEFINED";
}


DashArd::DashArd(const char* _accessId){
  accessId = _accessId;
}

void DashArd::init(){
  restClient = RestClient(host,port);
  restClient.dhcp();
}

// GET path
void DashArd::syncronize(){

  String path =  String(accessId) + "/metrics"  ;
  String response = "";
  
  // Create Json
  
  StaticJsonBuffer<200> jsonBuffer;
  JsonObject& root = jsonBuffer.createObject();
  root["device"] = accessId;
 

  
  any_key_t** keys;
  any_value_t* value;
  size_t keys_len, i;
  
  JsonArray& data = root.createNestedArray("metrics");
  if (HASHMAP_OK == hashmap_keys(metricsMap, &keys, &keys_len)) {
	  for (i = 0; i < keys_len; ++i) {
          if (HASHMAP_OK == hashmap_get(metricsMap, keys[i], &value)) {
			  data.add((char*)keys[i],(char*)value);
		  }
	  }
  }
  
  JsonArray& data2 = root.createNestedArray("pins");
  if (HASHMAP_OK == hashmap_keys(pinsMap, &keys, &keys_len)) {
	  for (i = 0; i < keys_len; ++i) {
          if (HASHMAP_OK == hashmap_get(pinsMap, keys[i], &value)) {String keyStr = "\"";
			  data2.add((char*)keys[i],(char*)value);
		  }
	  }
  }
/*
  int statusCode = restClient.post("/data",root.printTo(Serial), &response);
  Serial.print("Status code from server: ");
  Serial.println(statusCode);
  Serial.print("Response body from server: ");
  Serial.println(response);
	*/

}

void DashArd::pinsPoll(){

  //Read all Pins
   //hashmap_put(pinsMap, code, value);
   //...

}

void DashArd::setMeasure(const char* code, const char* value){
	hashmap_put(metricsMap, code, value);
}