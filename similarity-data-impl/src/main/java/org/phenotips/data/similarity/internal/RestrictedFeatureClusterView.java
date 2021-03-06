/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.phenotips.data.similarity.internal;

import org.phenotips.data.Feature;
import org.phenotips.data.similarity.AccessType;
import org.phenotips.ontology.OntologyTerm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of {@link org.phenotips.data.similarity.FeatureClusterView} that reveals the full patient information if the user has full access to
 * the patient, and only matching reference information for similar features if the patient is matchable.
 * 
 * @version $Id$
 * @since
 */
public class RestrictedFeatureClusterView extends DefaultFeatureClusterView
{
    /**
     * Constructor passing the {@link #match matched feature} and the {@link #reference reference feature}.
     * 
     * @param match the features in the matched patient, can be empty
     * @param reference the features in the reference patient, can be empty
     * @param access the access level of the match
     * @param root the root/shared ancestor for the cluster, can be {@code null} to represent unmatched features
     * @param score the score of the feature matching, or 0.0 if unmatched
     * @throws IllegalArgumentException if match or reference are null
     */
    public RestrictedFeatureClusterView(Collection<Feature> match, Collection<Feature> reference, AccessType access,
        OntologyTerm root, double score) throws IllegalArgumentException
    {
        super(match, reference, access, root, score);
    }

    @Override
    public Collection<Feature> getMatch()
    {
        if (this.access.isPrivateAccess()) {
            return Collections.emptySet();
        } else if (this.access.isOpenAccess()) {
            return super.getMatch();
        } else {
            List<Feature> hiddenFeatures = new ArrayList<Feature>(this.match.size());
            for (int i = 0; i < this.match.size(); i++) {
                hiddenFeatures.add(null);
            }
            return Collections.unmodifiableCollection(hiddenFeatures);
        }
    }
}
