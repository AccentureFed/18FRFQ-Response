##Jigsaw REST API

We enrich the data in a few ways: 
- We normalize the distribution_pattern by using NLP (natural language processing) to find which US states an item was distributed in.  This allows us to effectively search by state, where their API would not allow this due to no standard state syntax and other troubles, such as a search for “OR” (for Oregon) would return all recalls that had the word “or” in the statement which caused false positives.  Our API can also find distribution states that are listed as “on site retail” and map that to the manufacturing state, which is another limitation of the given API.
- We normalize the UPC codes using NLP to find all of the barcodes a recall affects.  In the FDA dataset, UPCs can be in the distribution_pattern or code_info payload, which makes it hard to work with.  We can now create an array of just the UPC codes for searching.
- We store the report_date so that we can order results by the date (most recent first).  The FDA API does not indicate that ordering is possible.
- For free-text searches, we restrict the search to product_description, reason_for_recall, recalling_firm, and barcodes to provide a better user experience and not searching on fields that are not public domain which allows for better results for our target user.
- Furthermore, we can return as many results as a user wants instead of only 100 at a time.  We also do not have a skip limit of 5,000 for a query that the API has.
- Lastly, we strip out the duplicate food recall data that is in the API.


All of the endpoints are assumed to have the host/port and application root at the beginning.  Such that `<host>/application/version` would expand to something like `https://jigsaw.agilex-devcloud.com/jigsaw/application/version`

###host/application/version - GET
Returns JSON of the application version number, as seen below:
 ```   
 {
      "version": "1.0.120"
    } 
 ```
The version can be read as `<major>.<minor>.<build_number>`


###host/appSettings/updateSettings - POST (URL Encoded Payload) - Requires to be logged in
    params:
        appAlert:	String, Required - The new home page alert banner text. To clear the banner, use an empty string.
Sets the settings, currently only the appAlert variable, for the application.  Upon a successful update, a status 200 is returned.  If an error occurs, a 501 is returned.


###host/appSettings/getSettings - GET
Returns JSON of the application settings, as seen below:
 ```
 {
      "appAlert": "This is the saved alert banner for the application"
 }
 ```
The appAlert is the only setting and it contains the text that the alert banner on the front page should display.  If it is empty, no banner should be displayed.


###host/foodRecall/count - GET or POST (URL Encoded Payload)
    params:
        stateCode:	String, Optional - The state to get recall count for.  If not present (or an invalid state), then no state specific searching will be performed. Can be any name or abbreviation of a state.
        startDate:	DateString, Optional - The date to start the count from in the format of yyyyMMdd If the date is in an invalid format, it will be ignored.  The date is inclusive (the date given will be included in the counts).
        endDate:	DateString, Optional - The date to end the count from in the format of yyyyMMdd If the date is in an invalid format, it will be ignored.  The date is inclusive (the date given will be included in the counts).
Gets a list of counts, grouped by severity, that match the given criteria.  Returns JSON, as seen below:
 ```
    {
      "stateCode": null,
      "results": [
        {
          "severity": "high",
          "count": 3757
        },
        {
          "severity": "low",
          "count": 269
        },
        {
          "severity": "medium",
          "count": 3779
        }
      ]
    }
 ```
The stateCode will be exactly what was given, even if it was an invalid state.  The results will contain an array of each severity and the count of recalls.  If no recalls were found for a given quest, the results array will be empty.


###host/foodRecall/recalls - GET or POST  (URL Encoded Payload)
    params:
        stateCode:	String, Optional - The state to get recalls for.  If not present (or an invalid state), then no state specific searching will be performed. Can be any name or abbreviation of a state.
        searchText:	String, Optional - Any free-form text to be matched with a UPC (exact match), Product Description (substring match), Recalling Firm (substring match), or Reason for Recall (substring match). If not given, ignored.
        startDate:	DateString, Optional - The date to start the recall search from in the format of yyyyMMdd If the date is in an invalid format, it will be ignored.  The date is inclusive (the date given will be included in the counts).
        endDate:	DateString, Optional - The date to end the recall search from in the format of yyyyMMdd If the date is in an invalid format, it will be ignored.  The date is inclusive (the date given will be included in the counts).
        limit:		Numeric, Optional - The number of results to return. If not given, 10 will be returned.
        skip:		Numeric, Optional - The page number to get. The first recall record will be from the offset of limit*skip.  If not given, 0 will be used (page numbers are 0-based).
Gets the set of recalls that matched the given query.  Returns JSON, as seen below:
```
    {
      "limit": 1,
      "skip": 0,
      "numResults": 7805,
      "results": [
        {
          "country": "US",
          "city": "Bronx",
          "reason_for_recall": "The Black & White Mini Cookies contain undeclared milk.  The product also contains soy lecithin, but the labeling only lists lecithin in the ingredient statement.",
          "classification": "Class I",
          "openfda": {
          },
          "recall_number": "F-2319-2015",
          "recalling_firm": "Sweet Sam's Baking Company, LLC",
          "product_quantity": "18,720 cases (equals 224,640 retail 2- packs)",
          "code_info": "all lots sold in Starbucks Company operated stores on or before Thursday, April 23rd, 2015.",
          "initial_firm_notification": "Press Release",
          "event_id": "71075",
          "product_type": "Food",
          "@epoch": 1433033362.3442,
          "normalized_distribution_pattern": "[DC, NJ, NY, SC, WV, FL, MD, PA, VA, CT, DE, GA, OH]",
          "distribution_pattern": "Initial: Florida, Maryland, New York, & New Jersey for further distribution to Starbucks Company-operated stores in Florida, New York, New Jersey, Pennsylvania, Washington DC, Maryland, Virginia, Connecticut, Delaware, Georgia, Ohio, South Carolina, and West Virginia.",
          "recall_initiation_date": "04\/23\/15",
          "normalized_barcodes": "[833282000495]",
          "state": "NY",
          "@id": "72b053cd1658bec7b924ae145fad290616a542fcddb3a12aaac8db718e0e6b5b",
          "product_description": "Black & White Mini Cookies, Net Wt. 2 oz. (56 g), SKU 408785, UPC code 833282000495, Manufactured for: Starbucks Coffee Company, Seattle, WA --- The product comes in a clear film package, each containing two cookies.  Each case contains 12 - 2 oz. packages (units).",
          "voluntary_mandated": "Voluntary: Firm Initiated",
          "status": "Ongoing",
          "report_date": "05\/27\/15"
        }
      ]
    }
```
In the results, the limit and skip will be passed back exactly as it was passed into the API.  The results will be an array of recalls with a size up to the limit requested (it may be less if there aren't enough results for your query to fill your limit).  The numResults field will contain the total records based on your given query.  This is helpful in determining how many pages of data there are, for example if limit was set to 10 and the numResults is 7,805, then there would be 781 pages (781 would be the highest skip parameter that could be used).  Most of the recall data is returned verbatim from the FDA API, with the exception of the normalized_distribution_pattern and the normalized_barcodes.  The normalized_distribution_pattern is an array of the US state codes that the product was distributed agains.  The normalized_barcodes is an array of all the UPC codes that this recall has affected.
