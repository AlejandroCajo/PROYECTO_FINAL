using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Clases
{
    public class Vertice
    {
        public int ID { get; private set; } 
        public Ciudad ciudadAsociada; 

        public Vertice sig = null; 

        
        public Vertice l1;
        public Vertice l2;
        public Vertice l3;
        public Vertice l4;
        public Vertice l5;
        public Vertice l6;

        public Vertice(Ciudad ciudad, int id)
        {
            ID = id;
            ciudadAsociada = ciudad;
            
            l1 = null; l2 = null; l3 = null; l4 = null; l5 = null; l6 = null;
        }

        
        public bool AgregarVecino(Vertice vecino)
        {
            if (vecino == null) return false;

        
            if (l1 == null) { l1 = vecino; return true; }
            if (l2 == null) { l2 = vecino; return true; }
            if (l3 == null) { l3 = vecino; return true; }
            if (l4 == null) { l4 = vecino; return true; }
            if (l5 == null) { l5 = vecino; return true; }
            if (l6 == null) { l6 = vecino; return true; }

            
            Console.WriteLine($"Advertencia: El vértice '{this.ciudadAsociada.Nombre}' no puede agregar más de 6 vecinos.");
            return false;
        }

 
        public bool EsVecinoDe(Vertice otroVertice)
        {
            if (otroVertice == null) return false;
            return (l1 == otroVertice || l2 == otroVertice || l3 == otroVertice ||
                    l4 == otroVertice || l5 == otroVertice || l6 == otroVertice);
        }

        public override string ToString()
        {
            return $"Vértice ID {ID}: {ciudadAsociada?.Nombre ?? "Sin Ciudad"}";
        }
    }

    public class Ciudad
    {
        public string Nombre { get; set; }

        public Ciudad(string nombre)
        {
            Nombre = nombre;
        }

        public override string ToString()
        {
            return Nombre;
        }
    }
}
