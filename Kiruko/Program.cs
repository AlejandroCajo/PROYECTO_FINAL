using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Clases;

namespace Kiruko
{
    internal class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Iniciando aplicación de grafo de ciudades (sin arrays)...");

        
            Ciudad cajamarca = new Ciudad("Cajamarca");
            Ciudad lima = new Ciudad("Lima");
            Ciudad trujillo = new Ciudad("Trujillo");
            Ciudad chiclayo = new Ciudad("Chiclayo");
            Ciudad arequipa = new Ciudad("Arequipa");
            Ciudad cusco = new Ciudad("Cusco");
            Ciudad piura = new Ciudad("Piura");

            Grafo miGrafoDeCiudades = new Grafo();

           
            miGrafoDeCiudades.AgregarVertice(cajamarca); // ID 0
            miGrafoDeCiudades.AgregarVertice(lima);      // ID 1
            miGrafoDeCiudades.AgregarVertice(trujillo);  // ID 2
            miGrafoDeCiudades.AgregarVertice(chiclayo);  // ID 3
            miGrafoDeCiudades.AgregarVertice(arequipa);  // ID 4
            miGrafoDeCiudades.AgregarVertice(cusco);     // ID 5
            miGrafoDeCiudades.AgregarVertice(piura);     // ID 6

            
            miGrafoDeCiudades.AgregarVertice(lima);

            Console.WriteLine("\n--- Agregando Aristas ---");
            
            miGrafoDeCiudades.AgregarArista(cajamarca, lima);
            miGrafoDeCiudades.AgregarArista(cajamarca, trujillo);
            miGrafoDeCiudades.AgregarArista(lima, chiclayo);
            miGrafoDeCiudades.AgregarArista(trujillo, lima);
            miGrafoDeCiudades.AgregarArista(chiclayo, arequipa);
            miGrafoDeCiudades.AgregarArista(lima, arequipa);
            miGrafoDeCiudades.AgregarArista(cajamarca, cusco);
            miGrafoDeCiudades.AgregarArista(piura, trujillo);


            miGrafoDeCiudades.AgregarArista(cajamarca, lima);
           
            miGrafoDeCiudades.MostrarAdyacencias();


            Console.WriteLine("Presiona cualquier tecla para salir...");
            Console.ReadKey();
        }
    }
    
}
