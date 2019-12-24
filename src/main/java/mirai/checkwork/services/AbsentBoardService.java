package mirai.checkwork.services;

import mirai.checkwork.common.AbsentRequest;

public interface AbsentBoardService {
    void takeAbsent(AbsentRequest req);
}
