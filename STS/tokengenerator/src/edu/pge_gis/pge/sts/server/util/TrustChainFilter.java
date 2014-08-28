package edu.pge_gis.pge.sts.server.util;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import edu.pge_gis.pge.sts.server.TrustChain;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class TrustChainFilter {

//    public static Iterable<TrustChain> getTcIterable(Collection<TrustChain> tc, final String p_appliesTo, final String p_tokenType, final String p_issuer){
//        Iterable<TrustChain> filter = Iterables.filter(tc, new Predicate<TrustChain>() {
//                                          @Override
//                                          public boolean apply(TrustChain input) {
//                                              
//                                              
//                                              String tcAppliesTo = input.getAppliesTo();
//                                              String tcTokenType = input.getTokenType();
//                                              String tcIssuer = input.getIssuer();
//                                              
//                                              boolean param_emptyAppliesTo = p_appliesTo==null || p_appliesTo.equals("") ;
//                                              boolean param_emptyTokenType = p_tokenType==null || p_tokenType.equals("") ; 
//                                              boolean param_emptyIssuer    = p_issuer==null    || p_issuer.equals("")   ;
//                                              
//                                              boolean field_emptyAppliesTo = tcAppliesTo==null || tcAppliesTo.equals("") ;
//                                              boolean field_emptyTokenType = tcTokenType==null || tcTokenType.equals("") ;
//                                              boolean field_emptyIssuer    = tcIssuer==null    || tcIssuer.equals("") ;
//                                              
////                                              return (emptyAppliesTo || (tcAppliesTo!=null && tcAppliesTo.equals(p_appliesTo))  ) && 
////                                                     (emptyTokenType || (tcTokenType!=null && tcTokenType.equals(p_tokenType))  ) &&
////                                                     (emptyIssuer    || (tcIssuer!=null    && tcIssuer.equals(p_issuer))        ) ;
//                                              return (param_emptyAppliesTo || (field_emptyAppliesTo || tcAppliesTo.equals(p_appliesTo))  ) && 
//                                                     (param_emptyTokenType || (field_emptyTokenType || tcTokenType.equals(p_tokenType))  ) &&
//                                                     (param_emptyIssuer    || (field_emptyIssuer    || tcIssuer.equals(p_issuer))        ) ;
//                                          }
//                                      });        
//        return filter;
//        
//    }
    
    public static Iterable<TrustChain> getTcIterable(Collection<TrustChain> tc, final String p_appliesTo, final String p_tokenType, final String p_issuer){
        Iterable<TrustChain> filter = Iterables.filter(tc, new Predicate<TrustChain>() {
                                          @Override
                                          public boolean apply(TrustChain trustChain) {
                                              
                                              
                                              
                                              String tcAppliesTo = trustChain.getAppliesTo();
                                              String tcTokenType = trustChain.getTokenType();
                                              String tcIssuer = trustChain.getIssuer();
                                              
                                              boolean param_emptyAppliesTo = p_appliesTo==null || p_appliesTo.equals("") ;
                                              boolean param_emptyTokenType = p_tokenType==null || p_tokenType.equals("") ; 
                                              boolean param_emptyIssuer    = p_issuer==null    || p_issuer.equals("")   ;
                                              
                                              boolean field_emptyAppliesTo = tcAppliesTo==null || tcAppliesTo.equals("") ;
                                              boolean field_emptyTokenType = tcTokenType==null || tcTokenType.equals("") ;
                                              boolean field_emptyIssuer    = tcIssuer==null    || tcIssuer.equals("") ;
                                              
                                              
                                              boolean matchAppliesTo = field_emptyAppliesTo || tcAppliesTo.equals(p_appliesTo.trim()) ;
                                              boolean matchIssuer    = field_emptyIssuer    || tcIssuer.equals(p_issuer.trim()) ;
                                              boolean matchTokenType = (field_emptyTokenType || param_emptyTokenType) || tcTokenType.equals(p_tokenType.trim());
                                              
                                              
                                              return matchAppliesTo && matchIssuer && matchTokenType;
                                          }
                                      });        
        return filter;
        
    }

    public static List<TrustChain> getTcList(Collection<TrustChain> tc, final String p_appliesTo, final String p_tokenType, final String p_issuer){
        return Lists.newLinkedList(getTcIterable(tc, p_appliesTo, p_tokenType, p_issuer));
    }
    
    public static TrustChain getTc(Collection<TrustChain> tc, final String p_appliesTo, final String p_tokenType, final String p_issuer){
        //TODO poner exception
        Iterator<TrustChain> tcIterator = getTcIterable(tc, p_appliesTo, p_tokenType, p_issuer).iterator();
        if (tcIterator.hasNext()) {
            return tcIterator.next();
        }
        return null ;
    }
    
}
