/*******************************************************************************
 * Copyright 2017 Hewlett-Packard Enterprise Development Company, L.P.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.hpe.octane.ideplugins.eclipse.ui.mywork.rowrenderer;

import static com.hpe.adm.octane.services.util.Util.getContainerItemForCommentModel;
import static com.hpe.adm.octane.services.util.Util.getUiDataFromModel;
import static com.hpe.octane.ideplugins.eclipse.ui.entitylist.DefaultRowEntityFields.getEntityDisplayName;

import com.hpe.adm.nga.sdk.model.EntityModel;
import com.hpe.adm.nga.sdk.model.ReferenceFieldModel;
import com.hpe.adm.octane.services.filtering.Entity;
import com.hpe.adm.octane.services.util.Util;
import com.hpe.octane.ideplugins.eclipse.ui.entitylist.custom.EntityModelRow;

public class CommentFieldSetter implements RowFieldSetter {

    @Override
    public void setField(EntityModelRow row, EntityModel entityModel) {

        try {
            String text = getUiDataFromModel(entityModel.getValue("text"));
            text = Util.stripHtml(text);

            ReferenceFieldModel owner = (ReferenceFieldModel) getContainerItemForCommentModel(entityModel);
            String ownerId = getUiDataFromModel(owner, "id");
            String ownerName = getUiDataFromModel(owner, "name");
            String ownerSubtype = getEntityDisplayName(Entity.getEntityType(owner.getValue())).toLowerCase();

            String entityName = "Comment on " + ownerSubtype + ": " + ownerId + " " + ownerName;
            row.setEntityName(entityName);
            row.setEntitySubTitle(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String[] getFieldNames() {
        return new String[] { "text", "owner_work_item", "owner_test", "owner_run" };
    }

}
