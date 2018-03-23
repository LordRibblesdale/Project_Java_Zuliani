public class Vector3<E> implements Sommabile<E> {
   private E x, y, z;

   Vector3(E x, E y, E z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public String toString() {
      return "( " + x.toString() + " " + y.toString() + " " + z.toString() + " )";
   }

   public E somma(E in) {

   }

   public Vector3<E> somma(Vector3<E> in) {
      return new Vector3(this.x.somma(in.x), this.y.somma(in.y), this.z.somma(in.y));
   }
}