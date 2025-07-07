using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Clases
{
    public class Grafo
    {
        private Vertice _cabeza; 
        private int _siguienteIDDisponible;

        public Grafo()
        {
            _cabeza = null; 
            _siguienteIDDisponible = 0; 
        }

      
        public void AgregarVertice(Ciudad ciudad)
        {
            if (ciudad == null)
            {
                Console.WriteLine("Error: La ciudad para el nuevo vértice no puede ser nula.");
                return;
            }

            
            Vertice nuevoVertice = new Vertice(ciudad, _siguienteIDDisponible);
            _siguienteIDDisponible++;

            
            Vertice actual = _cabeza;
            while (actual != null)
            {
                if (actual.ciudadAsociada == ciudad || actual.ciudadAsociada.Nombre == ciudad.Nombre)
                {
                    Console.WriteLine($"Advertencia: El vértice para '{ciudad.Nombre}' ya existe en el grafo.");
                   
                    _siguienteIDDisponible--;
                    return;
                }
                actual = actual.sig;
            }

           
            if (_cabeza == null)
            {
                _cabeza = nuevoVertice;
            }
            else
            {
                actual = _cabeza;
                while (actual.sig != null)
                {
                    actual = actual.sig;
                }
                actual.sig = nuevoVertice;
            }
            Console.WriteLine($"Vértice '{ciudad.Nombre}' (ID: {nuevoVertice.ID}) agregado.");
        }

      
        private Vertice BuscarVerticePorCiudad(Ciudad ciudad)
        {
            if (ciudad == null) return null;

            Vertice actual = _cabeza;
            while (actual != null)
            {
                if (actual.ciudadAsociada == ciudad || actual.ciudadAsociada.Nombre == ciudad.Nombre)
                {
                    return actual;
                }
                actual = actual.sig;
            }
            return null; 
        }

       
        public void AgregarArista(Ciudad origenCiudad, Ciudad destinoCiudad)
        {
            Vertice origen = BuscarVerticePorCiudad(origenCiudad);
            Vertice destino = BuscarVerticePorCiudad(destinoCiudad);

            if (origen == null || destino == null)
            {
                Console.WriteLine($"Error: No se puede agregar arista. Origen ('{origenCiudad?.Nombre}') o destino ('{destinoCiudad?.Nombre}') no encontrados en el grafo.");
                return;
            }

            
            if (origen.AgregarVecino(destino))
            {
                Console.WriteLine($"Arista agregada: {origen.ciudadAsociada.Nombre} -> {destino.ciudadAsociada.Nombre}");
            }
            else
            {
                Console.WriteLine($"Advertencia: No se pudo agregar la arista de '{origen.ciudadAsociada.Nombre}' a '{destino.ciudadAsociada.Nombre}' (posiblemente todos los enlaces están ocupados).");
            }

            
        }

        
        public void MostrarAdyacencias()
        {
            if (_cabeza == null)
            {
                Console.WriteLine("El grafo está vacío. No hay adyacencias para mostrar.");
                return;
            }

            Console.WriteLine("\n--- Adyacencias del Grafo (Formato tipo Matriz) ---");

          

            Console.Write("  xdd    "); 

           
            Vertice tempActualCol = _cabeza;
            while (tempActualCol != null)
            {
                string nombre = $"ID{tempActualCol.ID}"; 
                Console.Write(nombre.Substring(0, Math.Min(nombre.Length, 5)).PadRight(5) + " ");
                tempActualCol = tempActualCol.sig;
            }
            Console.WriteLine();

            
            Vertice actualFila = _cabeza;
            while (actualFila != null)
            {
               
                string nombreFila = $"ID{actualFila.ID}"; 
                Console.Write(nombreFila.Substring(0, Math.Min(nombreFila.Length, 5)).PadRight(5) + " ");

               
                Vertice actualCol = _cabeza;
                while (actualCol != null)
                {
                  
                    Console.Write((actualFila.EsVecinoDe(actualCol) ? "1" : "0").PadRight(5) + " ");
                    actualCol = actualCol.sig;
                }
                Console.WriteLine();
                actualFila = actualFila.sig;
            }
            Console.WriteLine("-------------------------------------------\n");
        }
    }
}


