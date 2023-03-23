package tn.accelengine.modules.start.hooks;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;
import tn.accelengine.core.domain.Document;
import tn.accelengine.modules.start.domain.Father;
import tn.accelengine.modules.std.port.in.DocumentInput;
import tn.accelengine.modules.workflow.annotations.AEApplicationReady;
import tn.accelengine.modules.workflow.annotations.AEHook;
import tn.accelengine.modules.workflow.domain.WorkflowHook;
import tn.accelengine.modules.workflow.domain.WorkflowTransition;
import tn.accelengine.modules.workflow.extend.AEWorkflowHookDetail;
import tn.accelengine.modules.workflow.port.in.WorkflowHookInput;
import tn.accelengine.modules.workflow.port.in.WorkflowTransitionInput;

@Slf4j
@AEHook
public class SynchroneHook extends AEWorkflowHookDetail<Father> {

    @Autowired
    private WorkflowTransitionInput transitionInput;

    @Autowired
    private DocumentInput documentInput;

    @Autowired
    private WorkflowHookInput workflowHookInput;

    @AEApplicationReady
    public void init() {
        Optional<WorkflowHook> aeWorkflowHookOptional = this.workflowHookInput.findOneByHookClassName(this.getClass().getName());
        if (!aeWorkflowHookOptional.isPresent()) {
            WorkflowHook hook = new WorkflowHook();
            Document document = this.documentInput.findOneByCodeOptional(Father.class.getSimpleName()).orElse(null);
            hook.setName("Hook synchrone");
            hook.setDocument(document);
            hook.setHookClassName(this.getClass().getName());
            hook.setStatus(true);
            hook.setAsync(false);
            hook = this.workflowHookInput.createNewDataAndGet(hook, false);
            WorkflowTransition transition1 = this.transitionInput.findOneByCode("INTERMEDIATE -> FINAL");
            var hooks = transition1.getHooks();
            hooks.add(hook);
            transition1.setHooks(hooks);
            this.transitionInput.updateData(transition1.getId(), transition1, false);
        }
    }

    @Override
    public void executeHook(Father data) {
        log.info("Start hook synchrone {}");

        log.info("end hook synchrone {}");
    }
}
