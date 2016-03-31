import json

go = {} # outbound_stops = 0
po = {} # inbound_stops = 1



def store(file,data): # file is the output file where you want the json stored and data is the Json object that you want to store
	with open(file, "w") as outfile:
		json.dump(data,outfile)




with open('lines.json') as data_file: #Lined.json is the json from the mbta
	data = json.load(data_file)
	for objects in data["direction"]:
		if objects["direction_id"] == "0": # All inbound trains
			for chunks in objects["stop"]:
				go[chunks["parent_station_name"]] = chunks["stop_id"]
			print(go)
			store("G-0.json", go)	#Store it in the file.
		else:							# All outbound trains
			for chunks in objects["stop"]:
				po[chunks["parent_station_name"]] = chunks["stop_id"]
			print(po)
			store("G-1.json", po)  #Store it in the file. 
