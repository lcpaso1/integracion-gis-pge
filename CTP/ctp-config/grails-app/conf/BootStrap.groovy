import ctp.config.ServicioGis
import ctp.config.Metodo
import ctp.config.ConfSeguridadPublica

class BootStrap {

    def init = { servletContext ->
    	String defaultDirFisica = "http://localhost:8080/GISWSwmsYwfs/GISWSwmsYwfs"
    	ServicioGis gis1 = new ServicioGis([nombre:"meteorologia",direccionLogica:"http://meteo.gub.uy/serviciosGis/Lluvias",direccionProxy:defaultDirFisica,publico:false]);
    	ServicioGis gis2 = new ServicioGis([nombre:"mtop_rutas",direccionLogica:"http://meteo.gub.uy/serviciosGis/Lluvias",direccionProxy:defaultDirFisica,publico:false]);

    	gis1.save();
    	gis2.save();

    	Metodo m1 = new Metodo([nombre:"getMap",nombreXml:"http://meteo.gub.uy/serviciosGis/Lluvias/getMap",servicioGis:gis1])
    	m1.save();

    	ConfSeguridadPublica sp1 = new ConfSeguridadPublica([usuario:"pubmet",rol:"ou:publico",perfil:"Publico_Meteorologia",servicioGis:gis1]);
    	sp1.save();

    }
    def destroy = {
    }
}
