package org.example;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("example-unit");
        EntityManager em = emf.createEntityManager();
        System.out.println("");


        try {
            em.getTransaction().begin();

            Factura factura1 = new Factura();
            factura1.setNumero(12);
            factura1.setFecha("14/09/2024");

            Domicilio dom = new Domicilio("Ayacucho", 62);
            Cliente cliente = new Cliente("Brian", "Carrasco", 44406332);
            cliente.setDomicilio(dom);
            dom.setCliente(cliente);

            factura1.setCliente(cliente);

            Categoria perecederos = new Categoria("Perecederos");
            Categoria lacteos = new Categoria("Lacteos");
            Categoria limpieza = new Categoria("Limpieza");

            Articulo art1 = new Articulo(20, "Yogurt Ser sabor vainilla", 250);
            Articulo art2 = new Articulo(50, "Detergente Magistral", 550);

            art1.getCategorias().add(perecederos);
            art1.getCategorias().add(lacteos);
            lacteos.getArticulos().add(art1);
            perecederos.getArticulos().add(art1);

            art2.getCategorias().add(limpieza);
            limpieza.getArticulos().add(art2);

            DetalleFactura det1 = new DetalleFactura();

            det1.setArticulo(art1);
            det1.setCantidad(2);
            det1.setSubtotal(40);


            factura1.getDetalles().add(det1);


            DetalleFactura det2 = new DetalleFactura();

            det2.setArticulo(art2);
            det2.setCantidad(1);
            det2.setSubtotal(250);

            factura1.getDetalles().add(det2);


            factura1.setTotal(120);

            //Factura factura1 = em.find(Factura.class,1L);

            //factura1.setNumero(85);

            em.persist(factura1);

            em.flush();

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}



        /*
        try {
            // Persistir una nueva entidad Person
            em.getTransaction().begin();

            Persona persona = Persona.builder().
                    nombre("Brian").
                    edad(22).

                    build();


            em.persist(persona);

            em.getTransaction().commit();

            // Actualizar la persona
            em.getTransaction().begin();
            persona.setEdad(35);
            em.getTransaction().commit();

            System.out.println("Persona actualizada: " + persona);

            // Eliminar la persona
            em.getTransaction().begin();
            em.remove(persona);
            em.getTransaction().commit();

            System.out.println("Persona eliminada: " + persona);




            // Consultar y mostrar la entidad persistida
    //        Persona personaRecuperada = em.find(Persona.class, persona.getId());
   //         System.out.println("Retrieved Persona: " + personaRecuperada.getNombre());

        }catch (Exception e){

            em.getTransaction().rollback();
            System.out.println(e.getMessage());
            System.out.println("No se pudo grabar la clase Persona");}

*/

// Cerrar el EntityManager y el EntityManagerFactory