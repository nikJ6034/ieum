<template lang="contactlist-component">

  <section class="greetings">
    <sub-layout>
      <div class="how-section1">
          <div class="row">
              <div class="col-md-4 how-img">
                  <img src="https://image.ibb.co/dDW27U/Work_Section2_freelance_img1.png" class="rounded-circle img-fluid" alt=""/>
              </div>
              <div class="col-md-8">
                  <h4>인사말</h4>
                              <h4 class="subheading">안녕하세요! 이음 마을학교입니다!</h4>
              <p class="text-muted">Freedom to work on ideal projects. On GetLance, you run your own business and choose your own clients and projects. Just complete your profile and we’ll highlight ideal jobs. Also search projects, and respond to client invitations.
                                  Wide variety and high pay. Clients are now posting jobs in hundreds of skill categories, paying top price for great work.
                                  More and more success. The greater the success you have on projects, the more likely you are to get hired by clients that use GetLance.</p>
              </div>
          </div>
      </div>
    </sub-layout>
  </section>

</template>

<script lang="js">
import SubLayout from '@/components/layouts/SubLayout'
  export default  {
    name: 'greetings',
    props: [],
    mounted() {

    },
    data() {
      return {

      }
    },
    methods: {

    },
    computed: {

    },
    components:{
      SubLayout
    }
}
</script>

<style scoped lang="scss">
  .greetings {

  }

.how-section1 h4{
    color: #ffa500;
    font-weight: bold;
    font-size: 30px;
}
.how-section1 .subheading{
    color: #3931af;
    font-size: 20px;
}
.how-section1 .row
{
    margin-top: 50px;
}
.how-img 
{
    text-align: center;
    margin: auto;
}
.how-img img{
    width: 40%;
}
</style>
