// This file can be replaced during build by using the `fileReplacements` array.
// `ng build` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  baseUrl: 'http://localhost:8081',
  TestingInboxEmails: [
    {
      "_id": "632660e3310bdf79298f3e0f",
      "sender": "am@mail.com",
      "receiver": "ahmed@mail.com",
      "subject": "first mail sent to ahmed",
      "body": "hi ahmed 1 111111111111111111111111111111111111",
      "seen": true,
      "date": new Date(),
    },
    {
      "_id": "63266135310bdf79298f3e23",
      "sender": "am@mail.com",
      "receiver": "ahmed@mail.com",
      "subject": "first mail sent to ahmed",
      "body": "hi ahmed 1 2222222222222222222",
      "seen": true,
      "date": new Date(),
    }
  ]
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.
