package test.java;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;


public class TesteGeral {

    @Test
    void classeEmetodoPrincipalExistem() {
        try {
           
            Class<?> mainClass = Class.forName("MySQLConnector.AtividadeFinal11");
            assertNotNull(mainClass, "A classe AtividadeFinal11.java deve existir.");

            
            Method mainMethod = mainClass.getDeclaredMethod("main", String[].class);
            assertNotNull(mainMethod, "O método 'main' deve existir.");

            
            assertTrue(Modifier.isPublic(mainMethod.getModifiers()), "O método 'main' deve ser público.");
            assertTrue(Modifier.isStatic(mainMethod.getModifiers()), "O método 'main' deve ser estático.");

            System.out.println("✅ Teste de estrutura de classe e método principal bem-sucedido.");

        } catch (ClassNotFoundException e) {
            
            System.err.println("❌ Erro: Classe AtividadeFinal11 não encontrada. Verifique o nome e o caminho do pacote.");
            assertTrue(false, "Classe AtividadeFinal11 não encontrada.");
        } catch (NoSuchMethodException e) {
            
            System.err.println("❌ Erro: Método 'main' não encontrado. Verifique a assinatura do método (public static void main(String[] args)).");
            assertTrue(false, "Método principal não encontrado ou com assinatura incorreta.");
        }
    }

    @Test
    void testePassandoDeExemplo() {
        
        assertTrue(true);
        System.out.println("✅ Teste de verificação do JUnit bem-sucedido.");
    }
}
