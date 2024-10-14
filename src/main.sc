# SC
require: named_patterns.sc
require: request_patterns/service_requests.sc
require: request_patterns/communication_requests.sc
require: request_patterns/info_requests.sc
require: response_patterns/service_responses.sc
require: response_patterns/communication_responses.sc
require: response_patterns/info_responses.sc
# JS
require: scripts/service.js
require: scripts/utils.js

init:
    bind("preProcess", function($context) {
        if (!$context.request.query){
            return
        }
        
        if ($context.request.query.length > 200) {
            $context.temp.targetState  = "/ResponseNoMatch";
        } else {
            $context.request.query = $context.request.query.replace(/[^a-zа-яё0-9\s]/gi, ' ');
        }
    });

    bind("postProcess", function($context) {
        $context.session.lastState = $context.currentState;
    });
 
