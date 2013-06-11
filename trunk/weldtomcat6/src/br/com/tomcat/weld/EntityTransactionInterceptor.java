package br.com.tomcat.weld;
import java.io.Serializable;

import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

/**
 * Declarative JPA EntityTransactions
 *
 * @author Gavin King
 */
@Transactional
@Interceptor
public class EntityTransactionInterceptor  implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private
    @Inject
    @Any
    EntityManager em;

    @AroundInvoke
    public Object aroundInvoke(InvocationContext ic) throws Exception
    {
    	
    	boolean act = !em.getTransaction().isActive();
    	
    	
    	
        if (act)
        {
            em.getTransaction().begin();
        }
        try
        {
        	
            Object result = ic.proceed();
            if (act)
            {
                em.getTransaction().commit();
            }
            return result;
        }
        catch (Exception e)
        {
            if (act)
            {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }
}