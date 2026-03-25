
/**
 * Write a description of class ModeloHaspMap here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
package gestionalmacen01.modelo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ModeloHashMap implements ModeloAbs
{
    private HashMap <Integer,Producto> mapa;
    
    public ModeloHashMap()
    {
       mapa=new HashMap  <Integer,Producto>();
    }

	@Override
	public boolean insertarProducto(Producto p) {
		if(mapa.containsKey(p.codigo)) {
			mapa.put(p.codigo, p);
			return true;
		}
		return false;
	}

	@Override
	public boolean borrarProducto(int codigo) {
		
		return (mapa.remove(codigo)!=null);
	}

	@Override
	public boolean modificarProducto(Producto nuevo) {
		// TODO Auto-generated method stub
		return (mapa.containsKey(nuevo.codigo));
	}

	@Override
	public Producto buscarProducto(int codigo) {
		
		return mapa.get(codigo);
	}

	@Override
	public void imprimirProductosTodos() {
		for(Producto p:mapa.values()) {
			System.out.println(p);
		}
	}

	@Override
	public List<Producto> obtenerProductos() {
		//RELLENAS EL ARRAYLIST CON LOS VALORES DE LA COLECCION
		//PERO NO PUEDES DEVOLVER mapa.values(); pues es una coleccion
		// jerarquia Collection -> List -> ArrayList
		return new ArrayList <Producto>(mapa.values());
	}

	@Override
	public List<Producto> obtenerProductosStockMin() {
		var listaStockMin = new ArrayList<Producto>();
		for (Producto p: mapa.values()) {
			if (p.stock <=p.stock_min) {
				listaStockMin.add(p);
			}
		}
		return listaStockMin;
	}

   
    
    
    
}
