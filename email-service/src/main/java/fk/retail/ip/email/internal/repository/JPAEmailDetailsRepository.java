package fk.retail.ip.email.internal.repository;

import com.google.inject.Inject;
import fk.retail.ip.email.model.EmailDetails;
import fk.sp.common.extensions.jpa.SimpleJpaGenericRepository;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * Created by agarwal.vaibhav on 09/05/17.
 */
public class JPAEmailDetailsRepository extends SimpleJpaGenericRepository<EmailDetails, Long> implements EmailDetailsRepository {

    @Inject
    public JPAEmailDetailsRepository(Provider<EntityManager> entityManagerProvider) {
        super(entityManagerProvider);
    }

    @Override
    public EmailDetails getEmailDetails(String stencilId, String groupName) {
        TypedQuery<EmailDetails> query = getEntityManager().createNamedQuery("findEmailDetailsByStencilId", EmailDetails.class);
        query.setParameter("stencilId", stencilId);
        query.setParameter("groupName", groupName);
        if(query.getResultList().isEmpty()) {
            return null;
        }else {
            EmailDetails emailDetails = query.getSingleResult();
            return emailDetails;
        }

    }
}
