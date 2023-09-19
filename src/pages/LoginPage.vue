<template>
  <v-container fluid>
    <v-row>
      <v-col cols="3">
      </v-col>
      <v-col cols="6" align="center" justify="center">
        <v-card elevation="3" outlined width="50%">
          <v-card-title class="justify-center">Login</v-card-title>
          <v-col cols="1"></v-col>
          <v-col cols="10">
            <v-text-field v-model="username" label="Username" filled></v-text-field>
            <v-text-field v-model="password" label="Password" filled></v-text-field>
            <v-btn block @click="loginFunction">
              Login
            </v-btn>
          </v-col>
          <v-col cols="1"></v-col>
        </v-card>
      </v-col>
      <v-col cols="3">
      </v-col>
    </v-row>
  </v-container>
</template>
  
<script>
export default {
  name: 'HelloWorld',

  data: () => ({
    username: "",
    password: "",
    savedUsers: [
      "TestUser"
    ],
    savedPasswords: [
      "userTestPass"
    ]
  }),
  methods: {
    loginFunction(){
      console.log("This is a test")
    }
  }
}
</script>
  