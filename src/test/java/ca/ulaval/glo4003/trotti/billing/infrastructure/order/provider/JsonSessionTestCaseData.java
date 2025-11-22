package ca.ulaval.glo4003.trotti.billing.infrastructure.order.provider;

public class JsonSessionTestCaseData {
    final static String VALID_SESSIONS_JSON = """
            [
              {
                "semester_code": "A25",
                "start_date": "2025-09-08",
                "end_date": "2025-12-20"
              },
              {
                "semester_code": "H26",
                "start_date":  "2026-01-10",
                "end_date": "2026-04-30"
              },
              {
                "semester_code": "E26",
                "start_date": "2026-05-05",
                "end_date": "2026-08-20"
              }
            ]
            """;

    final static String MISSING_SEMESTER_CODE_JSON = """
            [
              {
            	"start_date": "2025-04-30"
            	"end_date": "2025-01-10",
              }
            ]
            """;
}
