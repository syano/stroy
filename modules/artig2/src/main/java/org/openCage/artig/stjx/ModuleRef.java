package org.openCage.artig.stjx;
import java.util.ArrayList;
import java.util.List;
public class ModuleRef {
   private String name;
   public String getName() {
      return name;
   }
   public void setName( String name ) {
      this.name = name;
   }
    public String toString() {
       return "ModuleRef(name: " + getName() +" )";    }
}