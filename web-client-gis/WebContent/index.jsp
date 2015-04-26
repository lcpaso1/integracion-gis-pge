<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Servicio Público</title>
	<script type="text/javascript" src="../OpenLayers-2.13/OpenLayers.js"></script>
	
	<style type="text/css">
		#location { 
            float: right;
        }
		#infoMapa { 
            float: left;
        }
		#infoDiv { 
            float: left;
            margin-left: 20px;
        }
        #mapDiv { 
            width: 602px; 
            height: 440px; 
            border: solid 2px #808080;
        }
    </style>
	<script>
		var map;
		var layer;
		var bounds;
		var options;
		var info;
		var servicio;
		
		OpenLayers.IMAGE_RELOAD_ATTEMPTS = 5;
        OpenLayers.DOTS_PER_INCH = 25.4 / 0.28;
						 
		function showMap(pServicio){
			if (pServicio == "null"){
				servicio = 'montevideorural'; 
			}else {
				servicio = pServicio;
			}
			
			bounds = new OpenLayers.Bounds(
                552562.75, 6136575,
                589047.8125, 6159915
            );
			options = {
	                controls: [],
	                maxExtent: bounds,
	                maxResolution: 134.4580078125,
	                projection: "EPSG:32721",
	                units: 'm'
	            };
			map = new OpenLayers.Map("mapDiv", options);

			layer = new OpenLayers.Layer.WMS( 
					"",
				    "http://localhost:8280/ctp/http/ctp/"+servicio,
				    {
                        LAYERS: 'montevideorural',
                        STYLES: '',
                        format: 'image/png'
                    },
                    {
                       singleTile: true, 
                       ratio: 1, 
                       isBaseLayer: true,
                       yx : {'EPSG:32721' : false}
                    }
			);
			map.addLayer(layer);

			 // build up all controls
            map.addControl(new OpenLayers.Control.PanZoomBar({
                position: new OpenLayers.Pixel(2, 15)
            }));
            map.addControl(new OpenLayers.Control.Navigation());
            map.addControl(new OpenLayers.Control.Scale(document.getElementById('scale')));
            map.addControl(new OpenLayers.Control.MousePosition({element: document.getElementById('location')}));

			info = new OpenLayers.Control.WMSGetFeatureInfo({
	            url: "http://localhost:8280/ctp/http/ctp/"+servicio, 
	            queryVisible: true,
	            eventListeners: {
	                getfeatureinfo: function(event) {
	                	 document.getElementById('infoDiv').innerHTML = event.text;
		            }
	            }
	        });
	        map.addControl(info);
	        info.activate();

	        map.addControl(new OpenLayers.Control.LayerSwitcher());
	        map.zoomToMaxExtent();
	    }
	</script>
</head>
<body  onload="showMap('<%=request.getParameter("servicio")%>')">
	CATASTRO - MONTEVIDEO RURAL
	<div id="containerDiv">
		<div id="infoMapa">
			<div id="mapDiv"></div>
			<div id="wrapper">
		  		<div id="location">Ubicación</div>
        		<div id="scale"></div>
        	</div>
        </div>
        <div id="infoDiv"></div>
    </div>
</body>
</html>