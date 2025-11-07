package br.com.rocketseatproject.todolist.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

public class Utils {

    public static void copyNonNullProperties(Object source, Object target){
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));// Source veio do taskmodel que é a requisição atual Target veio do FindByID ou seja era o antigo que tinha no banco de dados
    }

    public static String[] getNullPropertyNames(Object source){
        final BeanWrapper src = new BeanWrapperImpl(source); // lê os atributos
        PropertyDescriptor[] pds = src.getPropertyDescriptors();// gera um array dos atributos

        Set<String> emptyNames = new HashSet<>();
        for(PropertyDescriptor pd : pds){ // verifica um atributo por vez
            Object srcValue = src.getPropertyValue(pd.getName()); // pega o valor da chave
            System.out.println("PD-> "+pd+"     srcValue->"+ srcValue);
            if(srcValue == null){
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);

    }
}
