<template>
  <v-container fluid>

    <v-card color="blue" class="ml-2 mr-2 mb-4">
      <v-card-title>
        Transactions for {{ currentUser }}
      </v-card-title>
    </v-card>

    <v-data-table :headers="headers" :items="transactions" :items-per-page="5"
      class="elevation-1 ml-2 mr-2" width="99%"></v-data-table>
  </v-container>
</template>
    
<script>
export default {
  name: 'HelloWorld',

  data: () => ({
    headers: [
      { text: "Item Title", align: 'start', sortable: false, value: 'title' },
      { text: "Item Category", value: 'category', },
      { text: "Item Specifications", value: 'specs', },
      { text: "Purchase Date", value: 'date', },
      { text: "Purchase Time", value: 'time', },
      { text: "Item Price", value: 'price', },
    ],
    currentUser: "Brandon Whatley",
    transactions: [
      { title: "T-Shirt", category: "Clothing", specs: "Blue Shirt", date: "September 18th, 2023", time: "9:00PM", price: "29.99" },
      { title: "T-Shirt", category: "Clothing", specs: "Blue Shirt", date: "September 18th, 2023", time: "9:00PM", price: "29.99" }
    ]
  }),
  methods: {
    loginFunction() {
      console.log("This is a test")
    }
  }
}
</script>
    