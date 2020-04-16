class Coordinate{
  constructor(lat, lon, speed_limit){
    this.lat = lat;
    this.lon = lon;
  }

  get_list_lat_lon(){
    return ([this.lat, this.lon]);
  }
}

addMapCopyright = function(map){
  L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
    maxZoom: 18,
    attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, ' +
      '<a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
      'Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
    id: 'mapbox.streets'
  }).addTo(map);
}

getListCoordinates = function(chunks){
  let newCoordinates = [];
  for (s of chunks){
    for (p of s.positions){
        newCoordinates.push(new Coordinate(p.latitude, p.longitude));
    }
  }
  return newCoordinates;
}

displayCoordinatesOnMap = function(coordinates, map, lastCoordinate){
  var color = "blue";
  for(var point of coordinates){
    var latLng = new L.LatLng(point.lat, point.lon)
    L.circle([point.lat, point.lon],  {radius: 10, color: color}).addTo(map);
    if (lastCoordinate != null){
        var poly = new L.Polyline([new L.latLng(lastCoordinate.lat, lastCoordinate.lon), latLng], {
            color: color,
            weight: 5,
            opacity: 1,
            smoothFactor: 1
        });
        poly.addTo(map);
    }
    lastCoordinate = point;
  }
}
