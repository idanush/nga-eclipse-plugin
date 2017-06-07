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
package com.hpe.octane.ideplugins.eclipse.ui.editor.job;

import java.util.Collection;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import com.hpe.adm.nga.sdk.model.EntityModel;
import com.hpe.adm.octane.services.CommentService;
import com.hpe.adm.octane.services.exception.ServiceRuntimeException;
import com.hpe.octane.ideplugins.eclipse.Activator;

public class GetCommentsJob extends Job {
    private CommentService commentService = Activator.getInstance(CommentService.class);
    private Collection<EntityModel> comments;
    private EntityModel parentEntiy;
    private boolean areCommentsLoaded = false;

    public GetCommentsJob(String name, EntityModel parentEntiy) {
        super(name);
        this.parentEntiy = parentEntiy;
    }

    @Override
    protected IStatus run(IProgressMonitor monitor) {
        monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
        try {
            comments = commentService.getComments(parentEntiy);
            areCommentsLoaded = true;
        } catch (ServiceRuntimeException e) {
            areCommentsLoaded = false;
        }
        monitor.done();
        return Status.OK_STATUS;
    }

    public Collection<EntityModel> getCoomentsForCurrentEntity() {
        if (comments.isEmpty()) {
            comments.add(new EntityModel("target_phase", "No comments"));
        }
        return comments;
    }

    public boolean areCommentsLoaded() {
        return areCommentsLoaded;
    }
}
